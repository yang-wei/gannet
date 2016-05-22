-- name: fetch-pages-by-domain
SELECT * FROM page WHERE domain_id = :domain_id

-- name: create-page<!
INSERT INTO page
(path, domain_id, status_code)
VALUES
(:path, :domain_id, :status_code)
