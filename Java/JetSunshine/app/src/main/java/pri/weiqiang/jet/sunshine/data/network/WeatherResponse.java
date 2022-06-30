package pri.weiqiang.jet.sunshine.data.network;


import java.util.List;


public class WeatherResponse {

    private CityBean city;
    private String cod;
    private Double message;
    private int cnt;
    private List<ListBean> list;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "city=" + city +
                ", cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                '}';
    }

    public static class CityBean {
        private int id;
        private String name;
        private CoordBean coord;
        private String country;
        private int population;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CoordBean getCoord() {
            return coord;
        }

        public void setCoord(CoordBean coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public static class CoordBean {
            private Double lon;
            private Double lat;

            public Double getLon() {
                return lon;
            }

            public void setLon(Double lon) {
                this.lon = lon;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }
        }
    }

    public static class ListBean {
        private Double dt;
        private TempBean temp;
        private Double pressure;
        private int humidity;
        private List<WeatherBean> weather;
        private Double speed;
        private int deg;
        private int clouds;

        public Double getDt() {
            return dt;
        }

        public void setDt(Double dt) {
            this.dt = dt;
        }

        public TempBean getTemp() {
            return temp;
        }

        public void setTemp(TempBean temp) {
            this.temp = temp;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public List<WeatherBean> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherBean> weather) {
            this.weather = weather;
        }

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public int getClouds() {
            return clouds;
        }

        public void setClouds(int clouds) {
            this.clouds = clouds;
        }

        public static class TempBean {
            private Double day;
            private Double min;
            private Double max;
            private Double night;
            private Double eve;
            private Double morn;

            public Double getDay() {
                return day;
            }

            public void setDay(Double day) {
                this.day = day;
            }

            public Double getMin() {
                return min;
            }

            public void setMin(Double min) {
                this.min = min;
            }

            public Double getMax() {
                return max;
            }

            public void setMax(Double max) {
                this.max = max;
            }

            public Double getNight() {
                return night;
            }

            public void setNight(Double night) {
                this.night = night;
            }

            public Double getEve() {
                return eve;
            }

            public void setEve(Double eve) {
                this.eve = eve;
            }

            public Double getMorn() {
                return morn;
            }

            public void setMorn(Double morn) {
                this.morn = morn;
            }
        }

        public static class WeatherBean {
            private int id;
            private String main;
            private String description;
            private String icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
