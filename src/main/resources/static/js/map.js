let map;

async function initMap(lat, lng, title) {
  // The location of Uluru
  const position = { lat: Number(lat), lng:  Number(lng)};
  // Request needed libraries.
  //@ts-ignore
  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");

  // The map, centered at Uluru
  map = new Map(document.getElementById("map"), {
    zoom: 15,
    center: position,
    mapId: "DEMO_MAP_ID",
  });

  // The marker, positioned at Uluru
  const marker = new AdvancedMarkerElement({
    map: map,
    position: position,
    title: title 
  });
}
let params = new URLSearchParams(location.search)

initMap(params.get("lat"), params.get("lng"), params.get("title"));