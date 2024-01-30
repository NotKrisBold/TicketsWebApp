## Lista tickets

URL: `/tickets`

Metodo: `GET`

Ritorna la lista di tickets in formato `json`.

## Risposta con successo

Status code: `200 OK`

Content-Type: `application/json`

Esempio risposta:

```json
[
  {
    "id": "f79c67c6-2aac-11ec-8d3d-0242ac130003",
    "title": "Ticket 1",
    "description": "Testo ticket 1",
    "author": "Marco"
  },
  {
    "id": "ff2500c0-2aac-11ec-8d3d-0242ac130003",
    "title": "Ticket 1",
    "description": "Testo ticket 2",
    "author": "Paolo"
  }
]
```

## Bad Request
Status code: `400 BAD REQUEST`

## Esempio

`GET` http://isin03.dti.supsi.ch:81/template/tickets