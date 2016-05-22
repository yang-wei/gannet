-- name: fetch-links-by-page
-- doc: list up links by providing domain
SELECT * FROM link WHERE page_id = :page_id

-- name: create-link<!
INSERT INTO link
(url, domain_id, page, status_code, content_type)
VALUES
(:url, :domain_id, :page, :status_code, :content_type)

-- name: update-link!
UPDATE link
SET
status_code = :status_code,
content_type = :content_type
WHERE
id = :id
