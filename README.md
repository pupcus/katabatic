# katabatic

This is a Clojure API wrapper for Weather Underground's API. I hope you find it useful.

## Usage
To use this program, you need to register at [Weather Underground](https://www.wunderground.com/weather/api/) and get a valid API key.

There are various 'data sets' that you can ask for from Weather Underground via their API.

For example, if you want to get tropical storms, you can get an Anvil Developer key, and then run this command:

```clojure
(get-weather {:apikey "APIKEY_GOES_HERE" :topics ["currenthurricane"]})
```
The default format is JSON, so it will give you JSON output, but you can also get XML output:
```clojure
(get-weather {:apikey "APIKEY_GOES_HERE" :topics ["currenthurricane"] :format "xml"})

```
Another feature is that you can ask for multiple topics at a time! See the API for more info on the topics you can use.

You must provide location information if you ask for anything besides hurricane info. For example:
```clojure
(get-weather {:apikey "APIKEY_GOES_HERE"
              :topics ["currenthurricane" "conditions" "alerts" "almanac"]
              :location {:zip "58718"} ;; <-- location by zipcode*
              :format "json"})
```
We have set it so that it will automatically choose your location (based on your IP) by default. But you can specify your location in many ways. Options include:
```clojure
;; zipcode*
{:zip "53718"}

;; lat/lon location**
{:lat "38.976"
 :lon "-77.157"}

;; airport weather station
{:airport "KJFK"}

;; personal weather station
{:pws "KCASANFR70"}

;; US city and state
{:city "Anchorage" :state "AK"}

;; World country and city
{:city "Paris" :country "France"}

;; specific ip address (example is google's nameserver address)
{:ip "8.8.8.8"}
```
\* The zip we used is in Madison, Winsconsin

\*\* You can get latitude and longitude from [Google Maps](https://google.com/maps).

## Helpers
There is a small DSL for writing requests to the API. Here is an example:
``` clojure
(require '[katabatic.core :as c])
(require '[katabatic.helpers :as h])
(require '[cheshire.core :as json])

(-> (h/with-api-key "APIKEY")
    (h/for-location :city "Seattle" :state "WA")
    (h/alerts)
    (h/conditions)
    (c/get-weather)
    (json/decode true))
```

See the helpers.clj (and the tests at some point) for all the helper functions you have availiable.

## Thanks
A huge thanks to Weather Underground for making the API, and to my dad for helping me out when I couldn't figure out how to do something.

## Badges
[![Clojars Project](https://img.shields.io/clojars/v/katabatic.svg)](https://clojars.org/katabatic)

## License
Copyright © 2017 jbmagination

Distributed under the Eclipse Public License either version 1.0 or any later version.

---

Powered by Weather Underground. Weather Underground is a registered trademark of The Weather Channel, LLC. both in the United States and internationally. The Weather Underground Logo is a trademark of Weather Underground, LLC.
[![Weather Underground](https://www.wunderground.com/logos/images/wundergroundLogo_4c_horz.jpg)](http://wunderground.com)
