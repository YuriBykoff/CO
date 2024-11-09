from flask import Flask, render_template
from flask_socketio import SocketIO, emit
import subprocess
import os
import threading

app = Flask(__name__)
socketio = SocketIO(app)

# Diretórios principais
project_path = os.path.abspath("..")  # Diretório raiz do projeto
src_path = os.path.join(project_path, "src/projeto_compiladores")
build_path = os.path.join(project_path, "build/projeto_compiladores")
input_file_path = os.path.join(src_path, "input/input_codigo.txt")
main_java_path = os.path.join(project_path, "Main.java")

process = None  # Variável global para armazenar o subprocesso

@app.route('/')
def index():
    return render_template('index.html')

@socketio.on('execute_code')
def execute_code(data):
    global process

    # Verificação de caminhos
    print(f"src_path: {src_path}")
    print(f"build_path: {build_path}")
    print(f"input_file_path: {input_file_path}")
    print(f"main_java_path: {main_java_path}")

    # Salva o código de entrada
    code = data.get('code')
    with open(input_file_path, 'w') as file:
        file.write(code)

    # Compila todos os arquivos .java de uma vez
    compile_command = f'javac -d "{build_path}" "{src_path}"/*.java "{src_path}/lexer"/*.java "{src_path}/parser"/*.java "{src_path}/tokens"/*.java'
    print(f"Executando comando de compilação: {compile_command}")

    compile_process = subprocess.run(
        compile_command,
        shell=True,
        cwd=src_path,  # Define o diretório de trabalho como src_path
        capture_output=True,
        text=True
    )

    if compile_process.returncode != 0:
        print(f"Erro ao compilar os arquivos Java: {compile_process.stderr}")
        emit('output', "Erro ao compilar os arquivos Java:\n" + compile_process.stderr)
        return
    else:
        print("Compilação dos arquivos Java bem-sucedida.")
        emit('output', "Compilação dos arquivos Java bem-sucedida.\n")

    # Compila Main.java se ele existir no diretório raiz
    if os.path.exists(main_java_path):
        compile_main_command = f'javac -d "{build_path}" "{main_java_path}"'
        print(f"Compilando Main.java com o comando: {compile_main_command}")
        
        compile_main_process = subprocess.run(
            compile_main_command,
            shell=True,
            cwd=project_path,  # Define o diretório de trabalho como project_path
            capture_output=True,
            text=True
        )

        if compile_main_process.returncode != 0:
            emit('output', "Erro ao compilar Main.java:\n" + compile_main_process.stderr)
            print(f"Erro ao compilar Main.java: {compile_main_process.stderr}")
            return
        else:
            emit('output', 'Compilação de Main.java bem-sucedida.\n')
            print("Compilação de Main.java bem-sucedida.")

            # Executa o Main.class
            execute_main_command = f'java -cp "{build_path}" Main'
            print(f"Executando Main com o comando: {execute_main_command}")
            
            process = subprocess.Popen(
                execute_main_command, 
                shell=True, 
                cwd=project_path, 
                stdin=subprocess.PIPE, 
                stdout=subprocess.PIPE, 
                stderr=subprocess.PIPE, 
                text=True
            )

            # Thread para enviar o output do Main.java em tempo real para o cliente
            def send_output():
                for line in process.stdout:
                    socketio.emit('output', line)

            threading.Thread(target=send_output).start()
    else:
        print("Erro: Main.java não foi encontrado no diretório raiz.")
        emit('output', "Erro: Main.java não foi encontrado no diretório raiz.")

@socketio.on('send_input')
def send_input(data):
    global process
    user_input = data.get('input', '')

    if process and process.stdin:
        process.stdin.write(user_input + '\n')
        process.stdin.flush()

if __name__ == '__main__':
    os.makedirs(build_path, exist_ok=True)
    socketio.run(app, debug=True)
