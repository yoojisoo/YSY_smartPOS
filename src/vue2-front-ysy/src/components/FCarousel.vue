<template>
	<div class="mt-n8">
		<!-- auto play 5000 : 5초 간격으로 자동으로 넘어감 -->
		<VueAgile autoplay :autoplay-speed="5000">
			<img
				class="slide"
				src="https://images.unsplash.com/photo-1506260408121-e353d10b87c7?ixlib=rb-1.2.1&amp;q=85&amp;fm=jpg&amp;crop=entropy&amp;cs=srgb&amp;w=1600&amp;fit=max&amp;ixid=eyJhcHBfaWQiOjE0NTg5fQ"
			/>
			<img
				class="slide"
				src="https://images.unsplash.com/photo-1523712999610-f77fbcfc3843?ixlib=rb-1.2.1&amp;q=85&amp;fm=jpg&amp;crop=entropy&amp;cs=srgb&amp;w=1600&amp;fit=max&amp;ixid=eyJhcHBfaWQiOjE0NTg5fQ"
			/>
			<img
				class="slide"
				src="https://images.unsplash.com/photo-1524260855046-f743b3cdad07?ixlib=rb-1.2.1&amp;q=85&amp;fm=jpg&amp;crop=entropy&amp;cs=srgb&amp;w=1600&amp;fit=max&amp;ixid=eyJhcHBfaWQiOjE0NTg5fQ"
			/>
			<template slot="prevButton">
				<i class="fas fa-chevron-left"></i>
			</template>
			<template slot="nextButton">
				<i class="fas fa-chevron-right"></i>
			</template>
		</VueAgile>
	</div>
</template>

<script>
import { VueAgile } from 'vue-agile';

export default {
	name: 'Carousel',
	components: {
		VueAgile,
	},
};
</script>

<style lang="scss">
@import url('https://unpkg.com/vue-agile@latest/dist/VueAgile.css');
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.1/css/all.min.css');

.agile {
	&__nav-button {
		background: transparent;
		border: none;
		color: white;
		cursor: pointer;
		font-size: 24px;
		height: 100%;
		position: absolute;
		top: 0;
		transition-duration: 0.3s;
		width: 80px;
		//&:hover {
		//	background-color: rgba(black, 0.5);
		//	opacity: 1;
		//}
		&--prev {
			left: 0;
		}
		&--next {
			right: 0;
		}
	}
	&__dots {
		bottom: 10px;
		left: 50%;
		position: absolute;
		transform: translateX(-50%);
	}
	&__dot {
		margin: 0 10px;
		button {
			background-color: transparent;
			border: 1px solid white;
			border-radius: 50%;
			cursor: pointer;
			display: block;
			height: 10px;
			font-size: 0;
			line-height: 0;
			margin: 0;
			padding: 0;
			transition-duration: 0.3s;
			width: 10px;
		}
		&--current,
		&:hover {
			button {
				background-color: white;
			}
		}
	}
}

// Slides styles
.slide {
	display: block;
	height: 40vh;
	object-fit: cover;
	width: 100%;
	//border-color: black;
	//border-width: 1px;

	//border-top-style: solid;
	//border-bottom-style: solid;
	-webkit-box-shadow: 0px 14px 29px 0px rgba(0, 0, 0, 0.75);
	-moz-box-shadow: 0px 14px 29px 0px rgba(0, 0, 0, 0.75);
	box-shadow: 0px 14px 29px 0px rgba(0, 0, 0, 0.75);
}
</style>
