<template>
	<v-navigation-drawer v-model="drawer" absolute temporary>
		<v-list-item>
			<v-list-item-avatar>
				<v-img src="https://randomuser.me/api/portraits/men/78.jpg"></v-img>
			</v-list-item-avatar>

			<v-list-item-content>
				<v-list-item-title>John Leider</v-list-item-title>
			</v-list-item-content>
		</v-list-item>

		<v-divider />

		<v-list dense>
			<v-list-item v-for="(item, idx) in headerMenu" :key="idx" :to="item.path">
				<v-list-item-icon>
					<v-icon v-if="item.pmenuId != '' || item.pmenuId != null">{{
						item.icon
					}}</v-icon>
				</v-list-item-icon>
				<v-list-item-content>
					<v-list-item-title v-if="item.pmenuId != '' || item.pmenuId != null">{{
						item.name
					}}</v-list-item-title>
				</v-list-item-content>
			</v-list-item>
		</v-list>
	</v-navigation-drawer>
</template>

<script>
export default {
	props: ['drawer', 'headerMenu'],
};
</script>

<style></style>
