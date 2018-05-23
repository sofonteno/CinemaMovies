<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="fr">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="">

<title>Movies</title>

<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet">
<link href="webjars/bootstrap-vue/1.3.0/bootstrap-vue.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.0-beta/css/bootstrap-select.min.css">

<!-- Custom styles for this template -->
<link href="resources/offcanvas.css" rel="stylesheet">
<link href="resources/welcome.css" rel="stylesheet">
<link href="resources/footer.css" rel="stylesheet">
</head>

<body class="bg-light">

	<c:import url="inc/navbar1.jsp" />

	<%-- 	<c:import url="inc/navbar2.jsp" /> --%>

	<main role="main" class="container" id="moviesList">
	<div class="p-3 my-3 text-white-50 bg-purple rounded box-shadow">
		<div class="row">
			<div class="col-4">
				<select class="selectpicker" id="sortByMark" title="Trier par : "
					data-width="100%">
					<option title="Trier par : Note globale" value="global" selected>Note globale</option>
					<option title="Trier par : Note Sens Critique" value="sc">Note Sens Critique</option>
					<option title="Trier par : Note Allociné" value="allocine">Note Allociné</option>
				</select>
			</div>
			<div class="col-3">
				<select class="selectpicker" id="daysFilter" v-model="selectedDays"
					title="Filtrer jour : " data-width="100%"
					data-selected-text-format="count > 2" multiple>
					<option v-for="day in days" :value="day"
						selected>{{day}}</option>
				</select>
			</div>
			<div class="col-5">
				<select class="selectpicker" id="theatersFilter"
					v-model="selectedTheaters" title="Filtrer salle : "
					data-width="100%" data-selected-text-format="count > 2" multiple>
					<option v-for="theater in theaters" :value="theater.theaterId"
						selected>{{theater.name}}</option>
				</select>
			</div>
		</div>
	</div>

	<div class="container">
		<template v-for="movie in movies"
			v-if="containsSelectedTheater(movie.theaters)">
		<div class="card mt-3">
			<div class="card-header">
				<ul class="nav nav-tabs card-header-tabs">
					<li class="nav-item">
						<a class="nav-link active" data-toggle="tab"
							v-bind="{ href: '#m'+movie.id }">Fiche</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" data-toggle="tab"
							v-bind="{ href: '#s'+movie.id }" href="#sessions">Séances</a>
					</li>
				</ul>
			</div>

			<div class="tab-content">
				<div class="tab-pane fade show active container"
					v-bind="{ id: 'm'+movie.id }">
					<div class="card-body">
						<div class="row">
							<div class="col-3">
								<img class="fit-content" :src="movie.imageUrl">
							</div>
							<div class="col" style="padding-left: 0 !important;">
								<div class="row">
									<div class="col-7 no-padding">
										<h4 class="card-title">{{movie.title}}</h4>
										<h6 class="card-subtitle mb-2 text-muted">Genre :
											{{movie.genre}}</h6>
									</div>

									<div class="col">
										<div class="row">
											<div class="col-4 text-center">
												<h1 style="color: var(--al-color)">{{movie.marks.Allocine}}</h1>
											</div>
											<div class="col-4 text-center">
												<h1 style="color: var(--ss-color)">{{movie.marks.SensCritique}}</h1>
											</div>
											<div class="col-4 text-center">
												<h1>{{movie.averageMark}}</h1>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div id="summary">
										<p class="collapse text-justify"
											v-bind="{ id: 'collapse'+movie.id }">{{movie.synopsis}}</p>
										<a class="collapsed" data-toggle="collapse"
											v-bind="{ href: '#collapse'+movie.id }" aria-expanded="false"
											aria-controls="collapseSummary"></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane fade container" v-bind="{ id: 's'+movie.id }">
					<div class="card-group" style="margin: 1rem;">
						<template v-for="daySession in movie.daySessions">
						<div class="card border" style="margin: 1rem;">
							<div class="card-header">{{daySession.day}}</div>
							<ul class="list-group list-group-flush">
								<template v-for="hourSession in daySession.hourSessions">
								<li class="list-group-item card-text">
									<h5 class="card-title">{{hourSession.hour}}</h5>
									<h6 class="card-subtitle mb-2 text-muted">{{hourSession.theater}}</h6>
								</li>
								</template>
							</ul>
						</div>
						</template>
					</div>
				</div>
				<div class="tab-pane container" id="menu2">...</div>
			</div>

		</div>
		</template>
	</div>

	</main>

	<div class="container" style="margin-bottom: 1rem;"></div>

	<footer class="footer">
		<div class="container">
			<span class="text-muted">Cinema movies</span>
		</div>
	</footer>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="webjars/jquery/3.0.0/jquery.min.js"></script>
	<script src="webjars/underscorejs/1.8.3/underscore-min.js"></script>
	<script src="https://unpkg.com/popper.js/dist/umd/popper.min.js"></script>
	<script src="webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.0-beta/js/bootstrap-select.min.js"></script>
	<!-- 	<script src="webjars/popper.js/1.11.1/dist/popper.min.js"></script> -->

	<script src="webjars/vue/2.1.3/vue.min.js"></script>
	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

	<script src="resources/offcanvas.js"></script>
	<script src="resources/welcome.js"></script>
</body>
</html>
