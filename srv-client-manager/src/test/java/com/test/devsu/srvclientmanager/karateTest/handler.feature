Feature: 'Testing handlerClient Api'

  Background: 'Set base url'
    Given url baseUrl

  Scenario: 'Testing CRUD clients'
    Given header Content-Type = 'application/json'
    Given request client
    Given method POST
    Then status 200
    And match $.name == "Jose Sevilla"
    And match $.dni == "9154236584"
    And def clientId = response.clientId
       #Testing method getallclients
    When method GET
    Then status 200
      #Testing method getOneClient
    Given path clientId
    When method Get
    Then status 200
    And match $ contains {clientId:"#notnull"}
    * print client
    #Testing method updateClient
    Given request {"name":"Pepe Sevilla", "dni":"9865448712", "age": 25}
    Given path clientId
    Given method PUT
    Then status 200
    And match $.name == "Pepe Sevilla"
    #Testing method deleteClient
    Given path clientId
    Given method DELETE
    Then status 200
    * print client