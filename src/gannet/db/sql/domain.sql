-- name: fetch-domains
-- doc: fetch domains
SELECT * FROM domain

-- name: create-domain<!
INSERT INTO domain
(name, domain_url)
VALUES
(:name, :domain_url)
