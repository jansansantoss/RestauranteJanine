Este é o projeto final do módulo de Java, desenvolvido como avaliação para a disciplina. O sistema simula o funcionamento completo de um restaurante, desde a gestão do cardápio e personalização de lanches até ao registo de pedidos e fecho do dia com cálculo de vendas e gorjetas.

## 📌 Funcionalidades Desenvolvidas
O sistema foi desenvolvido cumprindo integralmente os requisitos dos **Níveis 1, 2, 3 e todos os itens de Bônus**:

* **Nível 1 (O Restaurante Abre):** Criação das classes base (`Lanche` e `Pedido`), gestão do bloco de comandas com `HashMap` (`GerenciadorPedidos`) e interação com o utilizador (`MenuConsole` e `Main`).
* **Nível 2 (O Cardápio Ganha Vida):** Utilização da interface `ItemCardapio`, implementação do padrão de projeto **Factory** (`LancheFactory`) para criação padronizada de lanches e do padrão **Decorator** (`ComCheddar` e `ComBaconExtra`) para os adicionais.
* **Nível 3 (O Restaurante Fica Profissional):** Personalização dinâmica dos lanches (remoção de ingredientes), alteração/cancelamento de pedidos existentes, filtragem de combinações únicas no fecho do dia (utilizando `HashSet`) e tratamento de exceções de entrada de dados (`InputMismatchException`).
* **⭐ Bônus Completos⭐:**
    * Sistema financeiro Isolado (Pacote `financeiro`).
    * Implementação do padrão **Singleton** na classe `Caixa`.
    * Sobrescrita de `equals()` e `hashCode()` na classe `Transacao` para evitar registos duplicados no `HashSet`.
    * Funcionalidade de registo de **Gorjetas**.
    * Geração de um ficheiro executável `.jar` (via Maven).
    * Filtro extra para exibir a quantidade de lanches base diferentes vendidos.
    * 5 Testes automatizados no JUnit (2 obrigatórios + 3 extras).

## 🛠️ Arquitetura e Padrões de Projeto
O código foi estruturado respeitando o princípio de responsabilidade única (SOLID), dividido em quatro pacotes:
1. `app`: Responsável pela interação com o utilizador (`MenuConsole`), orquestração do sistema (`RestauranteFacade`) e ponto de entrada (`Main`).
2. `cardapio`: Representa a cozinha, contendo a classe base do produto, a fábrica e os decoradores.
3. `pedido`: Representa o balcão de comandas, com as lógicas de negócio de cada pedido individual e do aglomerado de pedidos.
4. `financeiro`: Representa a caixa registadora isolada, com controlo único das transações financeiras.

## 💡 Resposta à Pergunta para Pensar (Item 3.3)
**Por que motivo `X-Bacon + cheddar + bacon extra` e `X-Bacon + bacon extra + cheddar` contam como combinações diferentes no `HashSet<String>` e como resolver isso?**
O `HashSet` compara o texto exato da descrição (*String*). Como a ordem das palavras muda, o Java entende que são duas cadeias de caracteres diferentes, embora o lanche e o preço sejam os mesmos.
**Solução:** Em vez de concatenar as *strings* dos adicionais diretamente no texto, a solução ideal seria armazenar os nomes dos adicionais numa lista (`List<String>`) e ordená-la alfabeticamente (com `Collections.sort()`) antes de gerar o texto final no método `getDescricao()`. Assim, a ordem ficaria padronizada e o `HashSet` reconheceria as combinações como iguais.

## 🧪 Testes Automatizados (JUnit)
Foram desenvolvidos 5 testes na classe `RestauranteTest`, que cobrem:
1. Recusa de preço negativo no Lanche.
2. Cálculo correto do preço de um lanche com adicional (Decorator).
3. Cancelamento de pedido subtraindo corretamente o valor do total do dia.
4. Filtro correto de combinações únicas.
5. Manutenção do preço original de um lanche mesmo após a remoção de um ingrediente base.

## 🎥 Vídeo de Demonstração
Pode assistir à explicação do código e à demonstração do sistema a funcionar neste link:
[👉 **https://youtu.be/huue8NGX5w4**]

---
Desenvolvido por **Janine Santos**
