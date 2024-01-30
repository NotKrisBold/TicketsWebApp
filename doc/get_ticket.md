## Lettura ticket

URL: `/tickets/:ticket_id`

Metodo: `GET`

Ritorna in formato json il ticket con `id = ticket_id`.

## Risposta con successo

Status code: `200 OK`

Content-Type: `application/json`

Esempio risposta:

```json
{
  "id": "f79c67c6-2aac-11ec-8d3d-0242ac130003",
  "title": "Ticket 1",
  "description": "Testo ticket 1",
  "author": "Marco"
}
```

## Not Found

Se la risorsa con `id = ticket_id` non viene trovata.

Status code: `404 NOT FOUND`

## Bad Request
Status code: `400 BAD REQUEST`