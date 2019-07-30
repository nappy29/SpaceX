package com.example.spacex.data.model.api;

import com.example.spacex.data.model.others.Launch;
import com.example.spacex.data.model.others.Rocket;

import java.util.List;

public class RocketResponse {

        private List<Rocket> rocketList;

        public void setRocketList(List<Rocket> rocketList) {
            this.rocketList = rocketList;
        }

        public List<Rocket> getRocketList() {
            return rocketList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof RocketResponse)) {
                return false;
            }

            RocketResponse that = (RocketResponse) o;

            return rocketList.equals(that.rocketList);

        }

        @Override
        public int hashCode() {
            int result = rocketList == null? rocketList.hashCode() : null;
            return result;
        }
}
