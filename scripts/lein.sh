tmux new-session 'lein run' \; \
     split-window -h 'lein repl' \; \
     split-window 'lein cljsbuild auto' \; \
     select-layout tiled 
