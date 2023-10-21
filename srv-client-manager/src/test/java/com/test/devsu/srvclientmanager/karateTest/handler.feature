Feature: 'Testing handlerClient Api'

  Background: 'Set base url'
    Given url 'http://localhost:8080/clientes'
    * def client =
      """
      {
        "name":"Jose Sevilla",
        "gender":"M",
        "age":32,
        "dni":"9154236584",
        "address": "Calle # 51 sn",
        "phoneNumber":"535684185",
        "password":"98765",
        "status":true
      }
      """

  Scenario: 'Testing CRUD Clients'
    #Testing method getallclients
    When method GET
    Then status 200
    #Testing method getOneClient
    Given path '1'
    When method Get
    Then status 200
    And match $ contains {clientId:"#notnull"}
    #Testing method createClient
    Given request client
    Given method POST
    Then status 200
    And match $.name == "Jose Sevilla"
    And match $.dni == "9154236584"
    And def clientId = response.clientId
    And print "The response is: " + clientId
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