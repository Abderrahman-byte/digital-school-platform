# School Management Service :

## Endpoints :

| Endpoint                       | Method | Request data            | response data   | Description                      |
|--------------------------------|--------|-------------------------|-----------------|----------------------------------|
| /api/{version}/teachers        | GET    |                         |                 | Get list of joinned in teachers  |
| /api/{version}/teachers        | DELETE | ?id=<teacher-id\>       | {"ok": Boolean} | Remove teacher from school       |
| /api/{version}/requests        | GET    |                         |                 | Get teachers requests            |
| /api/{version}/requests/accept | POST   | {"id": "<teacher-id\>"} | {"ok": Boolean} | Accept teacher request           |
| /api/{version}/requests/reject | POST   | {"id": "<teacher-id\>"} | {"ok": Boolean} | Reject teacher request           |
| /api/{version}/archived        | GET    |                         |                 | Get formal teachers from archive |