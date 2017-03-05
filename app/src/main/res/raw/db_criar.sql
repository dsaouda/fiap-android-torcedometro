CREATE TABLE clube (
 id integer NOT NULL PRIMARY KEY,
 nome varchar(255) NOT NULL
);
CREATE TABLE torcedor (
 id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
 clube_id integer NOT NULL,
 nome varchar(255) NOT NULL,
 FOREIGN KEY (clube_id) REFERENCES clubes (id)
);