         var myGeoJSONPath = 'json/custom.geo.json';
      	 var map = L.map('map').setView([40.6, 30.8], 1.8);
        	function style(feature) {
                return {
                	radius: 8,
            	    fillColor: getColor(feature.properties.pop_est),
            	    color: "#000",
            	    weight: 1,
            	    opacity: 1,
            	    fillOpacity: 0.8,
            	    clickable: true
                };
        	}
                
        	function getColor(percent) {
        		return percent > 1000000000
                ? '#52143d'
                : percent > 500000000
                ? '#8f246b'
                : percent > 100000000
                ? '#b82e8a'
                : percent > 50000000
                ? '#d147a3'
                : percent > 10000000
                ? '#e085c2'
                : percent > 1000000
                ? '#f0c2e0'
                : '#faebf5';
        		
        	};
        
        $.getJSON(myGeoJSONPath,function(data){
     
           L.geoJson(data, {style: style}).addTo(map);
        })
        
			
        //add title and legend
        var grades = [1000000000, 500000000, 100000000, 50000000, 10000000, 1000000, 0];
        var div    = document.getElementById('legend');
		

        grades.forEach(function(grade, i) {
          div.innerHTML +=
            '<i style="background:' + getColor(grades[i] + 1) + '"></i>  Population : ' + grades[i] + '' + (grades[i - 1] ? '&ndash;' + grades[i - 1] + '' : '+') +'<br/>' ;
        });
         