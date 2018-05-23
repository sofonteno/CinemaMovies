var moviesUrl = 'http://localhost:8080/cimema-movies/rest/movies/sample';
var firstLoad = true;

Vue.filter('theater', function (value) {
	
})
	
var moviesList = new Vue({
	el : '#moviesList',
	data : {
		movies: [],
		theaters: [],
		selectedTheaters: []
	},
	beforeCreate: function () {
	    var firstLoad = true;
	 },
	created() {
		 axios.get(moviesUrl, {
	          transformResponse: axios.defaults.transformResponse.concat(function (data, headers) {
	              Object.values(data.movies).forEach(function (movie) {
	            	  
	            	  movie.daySessions.forEach(function(daySession) {  

	            		  daySession.hourSessions = daySession.hourSessions.sort(compareHourSessionsAsc);
	            	  });
	            	  movie.daySessions = movie.daySessions.sort(compareDaySessionsAsc);
	              });
	              return data;
	            })
	          })
		 .then(response => {
			 this.movies = response.data.movies.sort(compareMarkDescGlobal);
			 this.theaters = response.data.theaters;
		 })
		 .catch(e => {
			 console.log(e);
		 })
	 },
	 updated: function() {
		this.hideReadMore();
		 
		$(this.$refs.selectedTheaters).selectpicker('refresh');
		if(firstLoad){
			$("#theaters").selectpicker('selectAll');
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
		 }
	 }
	 
});

$(function() {
	$('#sortByMark').on('change', function(){
		
	   var selected = $('.selectpicker option:selected').val();
	   
	   if(selected == 'sc') {
		   moviesList.movies = moviesList.movies.sort(compareMarkDescSc);
	   } else if (selected == 'allocine') {
		   moviesList.movies = moviesList.movies.sort(compareMarkDescAllocine);
	   }
	});
});
// Vue.directive('sort', {
// bind: function() {
// $(this.el).selectpicker().on("change", function(e) {
// this.set($(this.el).val());
// }.bind(this));
// },
// update: function (value) {
// $(this.el).selectpicker('refresh').trigger("change");
// }
// });



function compareHourSessionsAsc(a, b) {
	if (a.hour > b.hour)
        return 1;
      if (a.hour < b.hour)
        return -1;
      return 0;
};

function compareHourSessionsDesc(a, b) {
	return -compareHourSessionsAsc(a, b);
};

function compareDaySessionsAsc(a, b) {
	if (a.day > b.day)
        return 1;
      if (a.day < b.day)
        return -1;
      return 0;
};

function compareDaySessionsDesc(a, b) {
	return -compareDaySessionsAsc(a, b);
};

function compareMarkDescGlobal(a, b) {
    if (a.averageMark < b.averageMark)
      return 1;
    if (a.averageMark > b.averageMark)
      return -1;
    return 0;
  }

function compareMarkDescSc(a, b) {
    if (a.marks.SensCritique < b.marks.SensCritique)
      return 1;
    if (a.marks.SensCritique > b.marks.SensCritique)
      return -1;
    return 0;
  }

function compareMarkDescAllocine(a, b) {
    if (a.marks.Allocine < b.marks.Allocine)
      return 1;
    if (a.marks.Allocine > b.marks.Allocine)
      return -1;
    return 0;
  }

function compareMarkAscGlobal(a, b) {
    return -compareMarkDescGlobal(a, b);
  }