# coding: utf-8
# language: pt

Funcionalidade: Clientes

  Cenário: Criação de um novo pagamento
    Dado que um novo pagamento foi criado
    E tenha um produto com valor 10
    E tenha um produto com valor 20
    Quando for requisitado a criacao pagamento via api
    Entao deve retornar o pagamento criado
    E o status deve ser igual a 201
    E o total deve ser de 30

  Cenário: Busca de pagamento via id
    Dado que exista um pagamento com um id
    Quando for requisitado a busca com um id existente
    Entao deve retornar o pagamento
    E o status deve ser igual a 200

  Cenário: Busca de pagamento via id, quando nao existe pagamento com o id solicitado
    Dado que nao exista um pagamento com o id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Quando for requisitado a busca via id "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    Entao nao deve retornar um pagamento
    E o status deve ser igual a 404

  Cenário: Busca de pagamento via status, quando existe pagamento com o status solicitado
    Dado que exista um pagamento com o status "APROVADO"
    Quando for requisitado a busca via status "APROVADO"
    Entao deve retornar os pagamento

