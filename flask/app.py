from flask import Flask, render_template
from flask_socketio import SocketIO, emit
import subprocess
import os
import threading
import time

app = Flask(__name__)
socketio = SocketIO(app)

# Caminhos principais
project_path = os.path.abspath("..")  # Diretório raiz do projeto
src_path = os.path.join(project_path, "src/projeto_compiladores")
build_path = os.path.join(project_path, "build")
input_file_path = os.path.join(src_path, "input/input_codigo.txt")
main_java_path = os.path.join(project_path, "Main.java")  # Agora em raiz
main_class_path = os.path.join(build_path, "Main.class")

process = None  # Variável global para armazenar o subprocesso

@app.route('/')
def index():
    return render_template('index.html')

@socketio.on('execute_code')
def execute_code(data):
    global process

    # Atualiza o conteúdo do arquivo .txt
    code = data.get('code')
    with open(input_file_path, 'w') as file:
        file.write(code)
    print(f"Arquivo .txt atualizado em: {input_file_path}")

    # Executa o compilador principal para gerar Main.java
    execute_translator_command = f'java -cp "{build_path}" projeto_compiladores.Projeto_Compiladores'
    print(f"Executando comando de tradução: {execute_translator_command}")
    
    translator_process = subprocess.run(
        execute_translator_command, shell=True, cwd=project_path,
        capture_output=True, text=True
    )

    if translator_process.returncode != 0:
        print(f"Erro ao executar Projeto_Compiladores: {translator_process.stderr}")
        emit('output', f"Erro ao executar Projeto_Compiladores:\n{translator_process.stderr}")
        return

    # Verifica se Main.java foi gerado no diretório raiz
    if not os.path.exists(main_java_path):
        print(f"Erro: Main.java não foi gerado em {project_path}")
        emit('output', f"Erro: Main.java não foi gerado corretamente.")
        return

    # Verifica se Main.java tem conteúdo
    if os.path.getsize(main_java_path) == 0:
        print("Erro: Código inválido: o Main.java não foi gerado corretamente.")
        emit('output', "Código inválido: o Main.java não foi gerado corretamente.")
        return

    # Exclui o Main.class antigo para garantir que a versão anterior não seja executada
    if os.path.exists(main_class_path):
        os.remove(main_class_path)
        print("Main.class antigo excluído.")

    # Compila o novo Main.java gerado no diretório raiz
    compile_main_command = f'javac -d "{build_path}" "{main_java_path}"'
    print(f"Compilando Main.java com o comando: {compile_main_command}")
    
    compile_main_process = subprocess.run(
        compile_main_command,
        shell=True,
        cwd=project_path,
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
