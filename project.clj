(defproject katabatic "1.0.0-SNAPSHOT"

  :description "weather underground api exploration"

  :url "https://github.com/jbmagination/katabatic"

  :scm {:url "git@github.com:jbmagination/katabatic.git"}

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[clj-http "3.4.1"]]

  :profiles {:dev {:resource-paths ["dev-resources"]
                   :dependencies   [[org.clojure/clojure "1.8.0"]
                                    [org.slf4j/slf4j-log4j12 "1.7.5"]
                                    [cheshire "5.7.0"]]}}

  :deploy-repositories [["snapshots"
                         {:url "https://clojars.org/repo"
                          :sign-releases false
                          :creds :gpg}]
                        ["releases"
                         {:url "https://clojars.org/repo"
                          :sign-releases false
                          :creds :gpg}]]

  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "--no-sign"]
                  ["deploy"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]

  :global-vars {*warn-on-reflection* true
                *assert* false})
