<template>
  <div id="map" ref="mapRef"></div>
</template>

<script>
import { onMounted, ref } from "@vue/runtime-core";
export default {
  setup(props, { emit }) {
    let mapRef = ref();
    let selectedRoom = ref({});

    onMounted(() => {
      //--------- MAZEMAP ----------//
      var map = new Mazemap.Map({
        // container id specified in the HTML
        container: mapRef.value,
        campuses: "default",
        center: { lng: 10.404674604795176, lat: 63.41558686615829 },
        zoom: 19,
        zLevel: 4,
        scrollZoom: true,
        doubleClickZoom: false,
        touchZoomRotate: false,
      });

      map.on("load", function () {
        // Initialize a Highlighter for POIs
        // Storing the object on the map just makes it easy to access for other things
        map.highlighter = new Mazemap.Highlighter(map, {
          showOutline: true,
          showFill: true,
          outlineColor: Mazemap.Util.Colors.MazeColors.MazeBlue,
          fillColor: Mazemap.Util.Colors.MazeColors.MazeBlue,
        });
        map.on("click", onMapClick);
      });

      // define a global
      var mazeMarker;

      function onMapClick(e) {
        clearPoiMarker(selectedRoom.value);
        var lngLat = e.lngLat;
        var zLevel = map.zLevel;

        // Fetching via Data API
        // NB: Adding optional campusId parameter, makes lookup much faster, but can be omitted
        Mazemap.Data.getPoiAt(lngLat, zLevel)
          .then((poi) => {
            // Place a marker on the map, or highlight the room
            placePoiMarker(poi);
            emit("poi", poi);
            selectedRoom.value = {
              buildingName: poi.properties.buildingName,
              roomName: poi.properties.title,
            };
          })
          .catch(function () {
            return false;
          });
      }

      function clearPoiMarker(poi) {
        if (mazeMarker) {
          mazeMarker.remove();
        }
        map.highlighter.clear();
      }

      function placePoiMarker(poi) {
        // Get a center point for the POI, because the data can return a polygon instead of just a point sometimes
        var lngLat = Mazemap.Util.getPoiLngLat(poi);

        mazeMarker = new Mazemap.MazeMarker({
          color: "#ff00cc",
          innerCircle: true,
          innerCircleColor: "#FFF",
          size: 34,
          innerCircleScale: 0.5,
          zLevel: poi.properties.zLevel,
        })
          .setLngLat(lngLat)
          .addTo(map);

        // If we have a polygon, use the default 'highlight' function to draw a marked outline around the POI.
        if (poi.geometry.type === "Polygon") {
          map.highlighter.highlight(poi);
        }
        map.flyTo({ center: lngLat, zoom: 19, speed: 0.5 });
      }
      //---------------------------------//
    });

    return {
      mapRef,
    };
  },
};
</script>

<style>
#map {
  height: 600px;
}
</style>
