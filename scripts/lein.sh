tmux new-session 'lein run' \; \
     split-window -h 'lein repl' \; \
     split-window 'lein figwheel' \; \
     select-layout tiled 
