(ns leiningen.new.trapperkeeper
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files
                                             year project-name sanitize-ns
                                             multi-segment]]
            [leiningen.core.main :as main]))

(def render (renderer "trapperkeeper"))

(defn trapperkeeper
  "A project template for trapperkeeper web applications.

Accepts a group id in the project name: `lein new trapperkeeper foo.bar/baz`"
  [name]
  (let [render        (renderer "trapperkeeper")
        project-name  (project-name name)
        main-ns       (multi-segment (sanitize-ns name) project-name)
        data {:raw-name name
              :name project-name
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)}]
    (main/info "Generating a project called" name "based on the 'trapperkeeper' template.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["doc/intro.md" (render "intro.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["src/{{nested-dirs}}_core.clj" (render "src/core.clj" data)]
             ["src/{{nested-dirs}}_service.clj" (render "src/service.clj" data)]
             ["src/{{nested-dirs}}_web_core.clj" (render "src/web_core.clj" data)]
             ["src/{{nested-dirs}}_web_service.clj" (render "src/web_service.clj" data)]
             ["test/{{nested-dirs}}_core_test.clj" (render "test/core_test.clj" data)]
             ["test/{{nested-dirs}}_service_test.clj" (render "test/service_test.clj" data)]
             ["test/{{nested-dirs}}_web_core_test.clj" (render "test/web_core_test.clj" data)]
             ["test/{{nested-dirs}}_web_service_test.clj" (render "test/web_service_test.clj" data)]
             ["dev-resources/bootstrap.cfg" (render "dev-resources/bootstrap.cfg" data)]
             ["dev-resources/config.conf" (render "dev-resources/config.conf" data)]
             ["dev-resources/logback-dev.xml" (render "dev-resources/logback-dev.xml" data)]
             ["dev-resources/logback-test.xml" (render "dev-resources/logback-test.xml" data)]
             ["dev/user.clj" (render "user.clj" data)]
             "resources")))
