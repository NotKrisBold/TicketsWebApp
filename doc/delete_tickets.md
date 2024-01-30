## Rimuovere un ticket


URL: `/tickets/:ticket_id`

Metodo: `DELETE`

Rimozione del ticket con `id = ticket_id`.


## Risposta con successo

Status code: `204 NO CONTENT`

## Not Found

Se la risorsa con `id = ticket_id` non viene trovata.

Status code: `404 NOT FOUND`

## Bad Request
Status code: `400 BAD REQUEST`