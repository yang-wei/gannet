LEIN = $(shell which lein)

build:
	$(LEIN) cljsbuild once && $(LEIN) uberjar 
