<template>
    <v-container>
        <v-divider class="ma-0 pa-0"/>
        <v-card-title>
            {{addr.addrType}}
            <v-spacer />
            <v-btn class="mr-2" outlined>삭제</v-btn>
            <v-btn outlined>수정</v-btn>
        </v-card-title>
        <v-card-text>
            <v-text-field label="우편번호"  prepend-icon="mdi-mailbox-up" v-model="addr.addrZipCode"></v-text-field>
            <v-text-field label="기본주소"  prepend-icon="mdi-map-marker" v-model="addr.addrDetail"></v-text-field>
            <v-text-field label="상세주소"  prepend-icon="mdi-home-map-marker" v-model="addr.addrEtc"></v-text-field>
            <v-text-field label="전화번호1" prepend-icon="mdi-phone" v-model="addr.phone1"></v-text-field>
            <v-text-field label="전화번호2" prepend-icon="mdi-phone" v-model="addr.phone2"></v-text-field>
        </v-card-text>
    </v-container>
</template>
<script>
export default {
    props: ['addr'],
    data() {
        return {
            idx : [1, 2, 3, 4, 5],
        }
    },
    methods: {
    },
    mounted() {
        console.log("this.addr ------->");
        console.log(this.addr);
    }

}
</script>

<style>

</style>