# wunderground

This is a WIP (work in progress/under construction). This is some clojure code designed to access weather underground api data and explore it. I am learning about how to use third party apis that have json results.

## Usage

To use this program, you need to register at [Weather Underground](https://www.wunderground.com/weather/api/) and get a valid api key. Then create a .lein-env file in the project root directory and put something like this in it:

```clojure
{
 :wunderground
 {
  :key "YOUR-API-KEY-GOES-HERE"
 }
}
```

## License

Copyright © 2017 jbmagination

Distributed under the Eclipse Public License either version 1.0 or any later version.
