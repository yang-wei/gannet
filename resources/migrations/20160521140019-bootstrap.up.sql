CREATE TABLE IF NOT EXISTS domain (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  domain_url VARCHAR(255),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);
--;;
CREATE TABLE IF NOT EXISTS page (
 id INT UNSIGNED NOT NULL AUTO_INCREMENT,
 path TEXT NOT NULL,
 domain_id INT UNSIGNED,
 status_code VARCHAR(3), -- for e.g: 200, 400
 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY(id),
 FOREIGN KEY(domain_id) REFERENCES domain(id) ON UPDATE cascade ON DELETE cascade
)
--;;
CREATE TABLE IF NOT EXISTS link (
 id INT UNSIGNED NOT NULL AUTO_INCREMENT,
 url TEXT NOT NULL, -- can be internal link or external link, if internal then no domain
 page_id INT UNSIGNED,
 status_code VARCHAR(3), -- for e.g: 200, 400
 content_type VARCHAR(255), -- for e.g: text/html
 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY(id),
 FOREIGN KEY(page_id) REFERENCES page(id) ON UPDATE cascade ON DELETE cascade
)
