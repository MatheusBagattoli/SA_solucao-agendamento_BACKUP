// Adicione um ouvinte de eventos aos botões de exclusão
document.querySelectorAll('.excluir').forEach(function(button) {
    button.addEventListener('click',
    function() {
        if (confirm('Confirma a exclusão?')) {

            const linha = this.closest('tr'); // Obtém a linha atual da tabela

            const id = this.dataset.id;

            //console.log("id=" + id);

            // Realize a chamada AJAX para excluir o recurso
            fetch(`/recursoExcluir/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (response.ok) {
                    // A exclusão foi bem-sucedida
                    console.log('Recurso excluído com sucesso.');

                    // Remove a linha da tabela após a exclusão
                    linha.remove();
                } else {
                    // A solicitação DELETE falhou
                    console.error('Erro ao excluir Recurso.');
                    alert('Erro ao excluir Recurso');
                }
            })
            .catch(error => {
                // Lidar com erros de rede ou outros erros
                console.error('Erro de rede:', error);
                alert('Erro de rede:' + error);
            });
        }
    });
});