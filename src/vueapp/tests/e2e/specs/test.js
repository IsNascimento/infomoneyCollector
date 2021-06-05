// https://docs.cypress.io/api/introduction/api.html

describe('e2e tests', () => {
  it('Visits the app and check for title', () => {
    cy.visit('http://localhost:8081/')
    cy.contains('h1', 'Notícias cadastradas')
  })

  it('Create new', () => {
    cy.visit('http://localhost:8080/')
    cy.get('#newButton').click()
    cy.get('#url').type('https://www.infomoney.com.br/stock-pickers/a-mais-forte-candidata-ao-titulo-de-proxima-weg/')
    cy.get('.btn-primary').click()
    cy.get('.v-toast__text').contains('Notícia cadastrada com sucesso.')
  })
})
