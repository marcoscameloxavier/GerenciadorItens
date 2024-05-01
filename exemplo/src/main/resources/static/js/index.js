window.onload = async function() {
   getItens();
   loadCarrinho2();
}
async function addItem() {
    var nome = document.getElementById("addNome").value;
    var quantidade = document.getElementById("addQuantidade").value;
    var valor = document.getElementById("addValor").value;
    var descritivo = document.getElementById("addDescritivo").value;

    var resposta = await fetch(`/api/itens`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nome: nome,
            qtdade: quantidade,
            valor: valor,
            descritivo: descritivo
        })
    });

    if (resposta.ok) {
        document.getElementById("addNome").value = "";
        document.getElementById("addQuantidade").value = "";
        document.getElementById("addValor").value = "";
        document.getElementById("addDescritivo").value = "";
        getItens();
    } else {
        alert("Falha ao adicionar item!");
    }

}


async function updateItem() {
    var id = document.getElementById("updateId").value;
    var nome = document.getElementById("updateNome").value;
    var quantidade = document.getElementById("updateQuantidade").value;
    var valor = document.getElementById("updateValor").value;
    var descritivo = document.getElementById("updateDescritivo").value;

    var resposta = await fetch(`/api/itens/${id}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nome: nome,
            qtdade: quantidade,
            valor: valor,
            descritivo: descritivo
        })
    });

    if (resposta.ok) {
        document.getElementById("updateId").value = "";
        document.getElementById("updateNome").value = "";
        document.getElementById("updateQuantidade").value = "";
        document.getElementById("updateValor").value = "";
        document.getElementById("updateDescritivo").value = "";
        var form = document.getElementById("updateItemForm");
        form.style.display = "none";
        var form = document.getElementById("addItemForm");
        form.style.display = "block";
        getItens();
    } else {
        alert("Falha ao atualizar item!");
    }

}

function cancelUpdateItem() {
    var form = document.getElementById("updateItemForm");
    form.style.display = "none";
    var form = document.getElementById("addItemForm");
    form.style.display = "block";
}

async function getItens() {
    var resposta = await fetch(`/api/itens`);
    var dados = await resposta.json();
    var itemsList = document.getElementById("itemsList");
    itemsList.innerHTML = ""; // Limpar a lista atual antes de adicionar novos itens

    for (var key in dados) {
        if (dados.hasOwnProperty(key)) {
            var item = dados[key];

            // Criar um novo elemento <li>
            var li = document.createElement('li');

            // Conteúdo do item
            var conteudo = `
                <div class="item-content">
                    <div class="item-detail">ID: ${item.id}</div>
                    <div class="item-detail">Nome: ${item.nome}</div>
                    <div class="item-detail">Quantidade: ${item.qtdade}</div>
                    <div class="item-detail">Valor: ${item.valor}</div>
                    <div class="item-detail">Descrição: ${item.descritivo}</div>
                </div>
                <div class="button-container">
                    <button type="button" class="carrinho-btn" onclick="addCarrinho(${item.id})">CARRINHO</button>
                    <button type="button" class="update-btn" onclick="preencherUpdate(${item.id})">UPDATE</button>
                    <button type="button" class="delete-btn" onclick="deleteItem(${item.id})">DELETE</button>
                </div>
            `;

            // Definir o conteúdo do <li>
            li.innerHTML = conteudo;

            // Anexar o <li> à lista <ul>
            itemsList.appendChild(li);
        }
    }
}

async function preencherUpdate(id) {

    //mostrar form que estava oculto
    var form = document.getElementById("updateItemForm");
    form.style.display = "block";
    var form = document.getElementById("addItemForm");
    form.style.display = "none";
    var resposta = await fetch(`/api/itens/${id}`);
    if (resposta.ok) {
        var item = await resposta.json();
        document.getElementById("updateId").value = item.id;
        document.getElementById("updateNome").value = item.nome;
        document.getElementById("updateQuantidade").value = item.qtdade;
        document.getElementById("updateValor").value = item.valor;
        document.getElementById("updateDescritivo").value = item.descritivo;
        console.log(item.descritivo);
    } else {
        console.error('Falha ao recuperar o item para atualização');
    }
}


async function deleteItem(id) {
    // Mostrar diálogo de confirmação
    var userConfirmed = confirm("Tem certeza que deseja deletar este item?");

    // Se o usuário confirmou, prosseguir com a deleção
    if (userConfirmed) {
        var resposta = await fetch(`/api/itens/${id}`, {
            method: "DELETE"
        });

        if (resposta.ok) {
            getItens(); // Atualizar a lista após a deleção
        } else {
            alert("Falha ao deletar item!");
        }
    }
}

async function addCarrinho(id) {

    var carrinho = document.getElementById("carrinhoResumo");
    carrinho.style.display = "block";
    // Construindo a URL com parâmetros de consulta
    var quantidade = 1;
    var carrinhoId = 1;
    var url = `/api/carrinho/${carrinhoId}/itens/${id}?quantidade=${quantidade}`;


    // Realizando a chamada fetch para adicionar o item ao carrinho
    var resposta = await fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        }
    });

    // Verifica se a resposta da adição ao carrinho foi bem-sucedida
    if (!resposta.ok) {
        alert("Falha ao adicionar item ao carrinho!");
    }

    loadCarrinho2();

}

async function loadCarrinho() {
    var carrinhoId = 1;
    var resposta = await fetch(`/api/carrinho/${carrinhoId}/itens`);
    var dados = await resposta.json();
    console.log("Dados do carrinho:", dados);
    for (var key in dados) {
        if (dados.hasOwnProperty(key)) {
            var item = dados[key];
        }
    }
}

async function loadCarrinho2() {
    var carrinhoId = 1; // Ajuste conforme necessário para o ID do carrinho do usuário
    var resposta = await fetch(`/api/carrinho/${carrinhoId}/itens`);

    if (resposta.ok) {
        var dados = await resposta.json();
        var itensCarrinho = document.getElementById('itensCarrinho');
        var totalItens = 0;
        var totalValor = 0.0;

        // Limpar itensCarrinho antes de adicionar novos itens
        itensCarrinho.innerHTML = '';

        for (var key in dados) {
            if (dados.hasOwnProperty(key)) {
                itemDto = dados[key];
                var itemDiv = document.createElement('div');
                itemDiv.classList.add('item-carrinho');
                itemDiv.innerHTML = `
                    <p>Nome: ${itemDto.nome}</p>
                    <div class="quantidade-container">
                        <button type="button" onclick="decreaseQuantity(${itemDto.id})">-</button>
                        <input type="number" id="${"carrinhoQuantidade" + itemDto.id}" value="${itemDto.quantidade}" readonly><br>
                        <button type="button" onclick="increaseQuantity(${itemDto.id})">+</button>
                        <!-- <button type="button" class="delete-carrinho-btn" onclick="deleteItemCarrinho(${itemDto.id})">DELETE</button>-->
                        <img src="images/delete.png" height="20px" onclick="deleteItemCarrinho(${itemDto.id})">
                    </div>
                    
                `;
                itensCarrinho.appendChild(itemDiv);
                // assumindo que você tem um campo `valor` em cada ItemDTO
                totalValor += itemDto.valor * itemDto.quantidade;
                totalItens += itemDto.quantidade;
            }
        }

        // Atualizar o total de itens e valor no carrinho
        document.getElementById('totalItens').textContent = totalItens;
        document.getElementById('totalValor').textContent = totalValor.toFixed(2);

    } else {
        console.error('Falha ao carregar o carrinho');
    }

    mostrarCarrinho();
}

async function decreaseQuantity(id) {
    var idElement = "carrinhoQuantidade" + id;
    var element = document.getElementById(idElement);
    var quantidade = element.value;
    if (quantidade > 1) {
        element.value = --quantidade;
        upddateItemCarrinho(id,quantidade);
    }
    else{
        deleteItemCarrinho(id);
    }
}

async function increaseQuantity(id) {
    var idElement = "carrinhoQuantidade" + id;
    var element = document.getElementById(idElement);
    var quantidade = element.value;
    element.value = ++quantidade;
    upddateItemCarrinho(id, quantidade);
}
async function upddateItemCarrinho(id, quantidade) {
    var carrinhoId = 1; // Ajuste conforme necessário para o ID do carrinho do usuário
    var resposta = await fetch(`/api/carrinho/${carrinhoId}/itens/${id}/${quantidade}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (resposta.ok) {
        loadCarrinho2();
    } else {
        alert("Falha ao atualizar item no carrinho!");
    }
}

async function deleteItemCarrinho(id) {
    var carrinhoId = 1; // Ajuste conforme necessário para o ID do carrinho do usuário
    console.log("Id do item a ser deletado:", id);
    var resposta = await fetch(`/api/carrinho/${carrinhoId}/itens/${id}`, {
        method: "DELETE"
    });

    if (resposta.ok) {
        loadCarrinho2();
    } else {
        alert("Falha ao deletar item do carrinho!");
    }
}

function mostrarCarrinho() {
    var carrinho = document.getElementById("carrinhoResumo");
    // Adiciona ou remove a classe que controla a visibilidade
    // carrinho.classList.toggle("visivel");
    // Adiciona a classe que controla a visibilidade
    carrinho.classList.add("visivel");
    var barraCarrinho = document.getElementById("barraCarrinho");
    // Alterar CSS de barraCarrinho para margin-right: 250px;
    barraCarrinho.style.marginRight = "280px";
}

function hideCarrinho() {
    var carrinho = document.getElementById("carrinhoResumo");
    carrinho.classList.remove("visivel"); // Remove a classe para esconder a barra lateral
    var barraCarrinho = document.getElementById("barraCarrinho");
    // Alterar CSS de barraCarrinho para margin-right: 0px;
    barraCarrinho.style.marginRight = "0px";
}

function toogleCarrinho() {
    var carrinho = document.getElementById("carrinhoResumo");
    carrinho.classList.toggle("visivel");
    var barraCarrinho = document.getElementById("barraCarrinho");

    if(carrinho.classList.contains("visivel"))
        barraCarrinho.style.marginRight = "280px";
    else
        barraCarrinho.style.marginRight = "0px";
}


