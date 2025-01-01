var playableData;
var $table = $('#table')

var search = function(){
    fetch("/playable",{
        method: "GET"
    }).then((response)=>{
        console.log(response);
    })
}

function ajaxRequest(params) {
    var url = '/playable'
    $.get(url + '?' + $.param(params.data)).then(function (res) {
      playableData = res;
      params.success(res);
    //   $("#table tr").click(function(){
    //     debugger;
    //     var tr = $(this);
    //     var dataRow = tr.prevAll().length;
    //     var data = playableData[dataRow];
    //     var coordinate = data["coordinate"];
    //     var name = data["name"];

    //   })
    })
  }

function coordinateFormatter(value, row){

    let coordinate = value.split(",");
    
    return  `<a class="btn btn-primary" href="/detail.html?id=${row.id}" role="button">Detail</a>`;
    return  `<button class="btn btn-info" onclick="changeMap(${coordinate[0]},${coordinate[1]},'${row.name}')">Map</button>`;

    // return  `<button onclick="window.open(\'/map.html?lat=${coordinate[0]}&lng=${coordinate[1]}&title=${row.name}\')">Map</button>`
}

function changeMap(lat, lng, name) {
    $("#mapFrame").attr("src", `map.html?lat=${lat}&lng=${lng}&title=${name}`);
}


