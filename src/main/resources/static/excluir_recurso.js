// Adiciona um ouvinte de eventos aos botões de exclusão de recurso
document.querySelectorAll('.excluir-recurso').forEach(function(button) {
    button.addEventListener('click',
    function() {
        if (confirm('Confirma a exclusão?')) {

            const linha = this.closest('tr'); // Obtém a linha atual da tabela

            const id = this.dataset.id;

            // Realiza a chamada AJAX para excluir o recurso
            fetch(`/recursoExcluir/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                response.text().then(mensagem => {
                    if (response.ok) {
                        console.log('Recurso excluído com sucesso.');
                        linha.remove();
                    } else {
                        console.error('Erro ao excluir Recurso:', mensagem);
                        alert(mensagem);
                    }
                });
            })
            .catch(error => {
                console.error('Erro de rede:', error);
                alert('Erro de rede:' + error);
            });
        }
    });
});