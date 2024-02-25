SELECT 
    Uzer.id,
    Uzer.name AS 'Nome',
    Uzer.username AS 'Login',
    Uzer.email AS 'E-Mail',
    gate.fullname(Role.id) AS 'Perfil'
FROM
    gate.Uzer
        JOIN
    Role ON Uzer.Role$id = Role.id