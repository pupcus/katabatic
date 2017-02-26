# katabatic

This is a WIP (work in progress/under construction). This is some clojure code for getting weather information from Weather Underground. I am learning about how to use third party APIs.

## Usage
To use this program, you need to register at [Weather Underground](https://www.wunderground.com/weather/api/) and get a valid API key. Then create a .lein-env file in the project root directory and put something like this in it:

```clojure
{
 :wunderground
 {
  :key "YOUR-API-KEY-GOES-HERE"
 }
}
```

There are various 'data sets' that you can ask for from Weather Underground via their API.

For example, if you want to get tropical storms, you can get an Anvil Developer key, and then run this command:

```clojure
(weather-info [:currenthurricane])
```
The default format is JSON, so it will give you JSON output, but you can also give XML output:
```clojure
(weather-info [:currenthurricane] {:format "xml"}])
```
You can ask for more then one topic at a time though! See the API for more info on the topics you can use.

You must provide location information if you ask for anything besides hurricane info. For example:
```clojure
(weather-info [:conditions :alerts])
```
For example, if you want to use your zip code you can do this:
```clojure
;; *the zip we used is in Madison, Wisconsin.
(weather-info [:conditions :forecast10day] {:zip "53718"})
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
**You can get latitude and longitude from [Google Maps](https://google.com/maps).

## Helpers
There is a small DSL for writing requests to the API. Here is an example:
``` clojure
(require '[katabatic.core :as c])
(require '[katabatic.helpers :as h])

(-> (h/alerts)
    (h/conditions)
    c/get-weather)
```

See the helpers.clj (and the tests at some point) for all the helper functions you have availiable.

## Thanks
A huge thanks to Weather Underground for making the API, and thanks to my dad for helping me out when I couldn't figure out how to do something.

## License
Copyright © 2017 jbmagination

Distributed under the Eclipse Public License either version 1.0 or any later version.

---

Powered by Weather Underground. Weather Underground is a registered trademark of The Weather Channel, LLC. both in the United States and internationally. The Weather Underground Logo is a trademark of Weather Underground, LLC.
![Weather Underground logo is supposed to be here, but since it didnt load, it wont appear](https://www.wunderground.com/logos/images/wundergroundLogo_4c_horz.jpg)
