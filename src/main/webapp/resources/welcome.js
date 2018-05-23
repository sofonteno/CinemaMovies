var moviesUrl = 'http://localhost:8080/cimema-movies/rest/movies/sample';
var firstLoad = true;

Vue.filter('theater', function (value) {
	
})
	
var moviesList = new Vue({
	el : '#moviesList',
	data : {
		movies: [],
		theaters: [],
		days: [],
		selectedTheaters: [],
		selectedDays: []
	},
	beforeCreate: function () {
	    var firstLoad = true;
	},
	created() {
		 axios.get(moviesUrl, {
	          transformResponse: axios.defaults.transformResponse.concat(function (data, headers) {
	              Object.values(data.movies).forEach(function (movie) {
	            	  
	            	  movie.daySessions.forEach(function(daySession) {  

	            		  daySession.hourSessions = _.sortBy(daySession.hourSessions, function(s){return s.hour;});
	            	  });
	            	  movie.daySessions = _.sortBy(movie.daySessions, function(d){return d.day;});
	              });
	              return data;
	            })
	          })
		 .then(response => {
			 this.movies = _.sortBy(response.data.movies, function(m){return -m.averageMark;});
			 this.theaters = response.data.theaters;
			 this.days = this.getAllDays();
		 })
		 .catch(e => {
			 console.log(e);
		 })
	 },
	 updated: function() {
		this.hideReadMore();
		 
		$("#theatersFilter").selectpicker('refresh');
		if(firstLoad){
			$("#theatersFilter").selectpicker('selectAll');
			$("#daysFilter").selectpicker('selectAll');
			firstLoad = false;
		}
	 },
	 computed: {
		 
	 },
	 
	 methods: {
		 hideReadMore : function() {
			var maxLength = 400;
			$(".collapse").each(function(){
				var length = $(this).text().length;
				var id = $(this).attr('id');
				
				if (length < maxLength) {
					
					$(".collapsed[href='#"+id+"']").hide();
				}
			});
		},
		
		containsSelectedTheater: function(theaters) {
			var movieTheatersIds = _.pluck(theaters, 'theaterId');
			return _.some(movieTheatersIds, function(id) { return _.contains(moviesList.selectedTheaters, id); }); 
		},
		
		getAllDays: function() {
			
			var sessions = _.pluck(this.movies, 'daySessions');
			console.log(sessions);
		}
	 }
	 
});


$(function() {
	$('#sortByMark').on('change', function(){
		
	   var selected = $('.selectpicker option:selected').val();
	   
	   if(selected == 'sc') {
		   moviesList.movies = _.sortBy(moviesList.movies, function(m){return -m.marks.SensCritique;});
	   } else if (selected == 'allocine') {
		   moviesList.movies = _.sortBy(moviesList.movies, function(m){return -m.marks.Allocine;});
	   }
	});
	
});