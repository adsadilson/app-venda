# Venda-app 💻
 
Projeto proposto pelo Digital Innovation com a finalidade de gerenciamento estoque de cerveja, porém fiz um mini CRUD de um sistema de venda onde abranger o cadastro de pessoas, produtos e vendas, com **atualizações automáticas do estoque**.

Todos os recursos estão desenvolvidos no padrão REST atendendo os requisitos necessários, foram gerados algumas validações bem como restrições que irei cita-las logo abaixo:

Em cadastro de pessoas alguns campos são requeridos além dessas obrigatoriedades também foi validado o não cadastramento da pessoa com o mesmo cpf.

Em cadastro de produtos todos os campos são requeridos e alguns possuir restrições especifica por ex: quantidade min e max de caracteres e valores inicial, também não é permitido cadastrar o mesmo produto com o mesmo nome e código de barra. 

Em cadastro de vendas o campo forma de pagamento e os itens são requeridos já o campo cliente é opcional. No ato da venda será verificar a disponibilidade do estoque bem como a situação do cliente para vendas aprazo não podendo ser realizar a venda para clientes bloqueados ou suspensos. **Enfatizo** atualizações do estoque no ato da venda e ou na exclusão.

Foi gerando alguns testes de integração e de API para a classe de venda.

## https://venda-app.herokuapp.com/vendas

# Tecnologias Utilizadas
-	Java
-	Spring Boot
-	JPA / Hibernate
-	Maven
-	PostgreSQL

# Projeto de expiração...
![tela de venda](https://user-images.githubusercontent.com/7797330/126247829-b2ec99b9-20d4-48cb-9c20-dc89ad55e988.gif)





