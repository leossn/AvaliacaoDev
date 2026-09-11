CREATE TABLE IF NOT EXISTS funcionario (
    rowid INT AUTO_INCREMENT PRIMARY KEY,
    nm_funcionario VARCHAR(100) NOT NULL
);
INSERT INTO funcionario (nm_funcionario) VALUES ('João'), ('Maria'), ('José'), ('Joana');

CREATE TABLE IF NOT EXISTS agenda (
    rowid INT AUTO_INCREMENT PRIMARY KEY,
    nm_agenda VARCHAR(100) NOT NULL,
    ds_periodo VARCHAR(50) NOT NULL
);
INSERT INTO agenda (nm_agenda, ds_periodo) VALUES ('Agenda Manhã', '1'), ('Agenda Tarde', '2'), ('Agenda Geral', '3');

CREATE TABLE IF NOT EXISTS compromisso (
    rowid INT AUTO_INCREMENT PRIMARY KEY,
    cd_funcionario INT NOT NULL,
    cd_agenda INT NOT NULL,
    dt_compromisso DATE NOT NULL,
    hr_compromisso VARCHAR(5) NOT NULL,
    FOREIGN KEY (cd_funcionario) REFERENCES funcionario(rowid),
    FOREIGN KEY (cd_agenda) REFERENCES agenda(rowid)
);