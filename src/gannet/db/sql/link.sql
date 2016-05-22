-- name: fetch-links-by-domain
-- doc: list up links by providing domain
SELECT * FROM link WHERE domain_id = :domain_id

-- name: create-domain<!
INSERT INTO link
(url, domain_id, page, status_code, content_type)
VALUES
(:url, :domain_id, :page, :status_code, :content_type)

-- name: update-domain!
UPDATE link
SET
status_code = :status_code,
content_type = :content_type
WHERE
id = :id
