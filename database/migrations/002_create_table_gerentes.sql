CREATE TABLE gerentes (
	id INT AUTO_INCREMENT PRIMARY KEY,
    equipe INT,
    FOREIGN KEY (id) REFERENCES funcionarios(id)
);