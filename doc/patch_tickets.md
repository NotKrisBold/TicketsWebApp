## Modifica ticket, con patch

URL: `/tickets/:ticket_id`

Metodo: `PATCH`

Modifica il ticket con `id = ticket_id`,
modificando  solo i campi inviati in formato `json`.
Ritorna in formato `json` il ticket modificato.

## Richiesta

Content-Type: `application/json`

Corpo:

```json
{
  "title": "Ticket 1 modificato con patch"
}
```

## Risposta con successo

Status code: `200 OK`

Content-Type: `application/json`

Esempio risposta:

```json
{
  "id": "f79c67c6-2aac-11ec-8d3d-0242ac130003",
  "title": "Ticket 1 modificato con patch",
  "description": "Testo ticket 1",
  "author": "Marco"
}
```

## Not Found

Se la risorsa con `id = ticket_id` non viene trovata.

Status code: `404 NOT FOUND`

## Bad Request
Status code: `400 BAD REQUEST`