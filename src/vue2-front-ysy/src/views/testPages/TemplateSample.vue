<template>
    <v-container class="main_layout_container">
        <v-row class="main_layout_row">
            <v-col class="main_layout_col" cols="12" md="10" lg="10" xl="10">
                <v-row justify="center" style="height: inherit" class="pt-5">
                    <v-col cols="12">
                        <v-row>
                            <v-col
                                v-for="(chart, i) in charts"
                                :key="`chart-${i}`"
                                cols="12"
                                md="6"
                                lg="4"
                            >
                                <test-material-chart-card
                                    :color="chart.color"
                                    :data="chart.data"
                                    :options="chart.options"
                                    :responsive-options="chart.responsiveOptions"
                                    :title="chart.title"
                                    :type="chart.type"
                                >
                                    <template #subtitle>
                                        <div class="font-weight-light text--secondary">
                                            <div v-html="chart.subtitle" />
                                        </div>
                                    </template>

                                    <template #actions>
                                        <v-icon class="mr-1" small>mdi-clock-outline</v-icon>
                                        <span class="text-caption grey--text font-weight-light" v-text="chart.time" />
                                    </template>
                                </test-material-chart-card>
                            </v-col>
                        </v-row>
                    </v-col>
                    <v-col
                        v-for="({ actionIcon, actionText, ...attrs }, i) in stats"
                        :key="i"
                        cols="12"
                        md="6"
                        lg="3"
                    >
                        <test-material-stats-card v-bind="attrs">
                            <template #actions>
                                <v-icon class="mr-2" small v-text="actionIcon" />
                                <div class="text-truncate">{{ actionText }}</div>
                            </template>
                        </test-material-stats-card>
                    </v-col>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
// import Vue from 'vue'
import TestMaterialStatsCard from '@/components/1_molecules/cards/TestMaterialStatsCard.vue'
import TestMaterialChartCard from '@/components/1_molecules/cards/TestMaterialChartCard.vue'

// const lineSmooth = Vue.chartist.Interpolation.cardinal({ tension: 0 })

export default {
    name: 'TemplateSample',
    components: { TestMaterialStatsCard, TestMaterialChartCard },
    data: () => ({
        charts: [
            {
                type: 'Bar',
                color: 'primary',
                title: 'Website Views',
                subtitle: 'Last Campaign Performance',
                time: 'updated 10 minutes ago',
                data: {
                    labels: ['Ja', 'Fe', 'Ma', 'Ap', 'Mai', 'Ju', 'Jul', 'Au', 'Se', 'Oc', 'No', 'De'],
                    series: [[542, 443, 320, 780, 553, 453, 326, 434, 568, 610, 756, 895],],
                },
                options: {
                    axisX: {
                        showGrid: false,
                    },
                    low: 0,
                    high: 1000,
                    chartPadding: {
                        top: 0,
                        right: 5,
                        bottom: 0,
                        left: 0,
                    },
                },
                responsiveOptions: [
                    ['screen and (max-width: 640px)', {
                        seriesBarDistance: 5,
                        axisX: {
                            labelInterpolationFnc: function (value) {
                                return value[0]
                            },
                        },
                    }],
                ],
            },
            {
                type: 'Line',
                color: 'success',
                title: 'Daily Sales',
                subtitle: '<i class="mdi mdi-arrow-up green--text"></i><span class="green--text">55%</span>&nbsp;increase in today\'s sales',
                time: 'updated 4 minutes ago',
                data: {
                    labels: ['12am', '3pm', '6pm', '9pm', '12pm', '3am', '6am', '9am'],
                    series: [[230, 750, 450, 300, 280, 240, 200, 190],],
                },
                options: {
                    // lineSmooth,
                    low: 0,
                    high: 1000, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
                    chartPadding: {
                        top: 0,
                        right: 0,
                        bottom: 0,
                        left: 0,
                    },
                },
            },
            {
                type: 'Line',
                color: 'info',
                title: 'Completed Tasks',
                subtitle: 'Last Campaign Performance',
                time: 'campaign sent 26 minutes ago',
                data: {
                    labels: ['M', 'T', 'W', 'T', 'F', 'S', 'S'],
                    series: [[12, 17, 7, 17, 23, 18, 38],],
                },
                options: {
                    // lineSmooth,
                    low: 0,
                    high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
                    chartPadding: {
                        top: 0,
                        right: 0,
                        bottom: 0,
                        left: 0,
                    },
                },
            }
        ],
        stats: [
            {
                actionIcon: 'mdi-alert',
                actionText: 'Get More Space...',
                color: '#FD9A13',
                icon: 'mdi-sofa-single',
                title: 'Bookings',
                value: '184',
            },
            {
                actionIcon: 'mdi-tag',
                actionText: 'Tracked from Google Analytics',
                color: 'primary',
                icon: 'mdi-chart-bar',
                title: 'Website Visits',
                value: '75.521',
            },
            {
                actionIcon: 'mdi-calendar-range',
                actionText: 'Last 24 Hours',
                color: 'success',
                icon: 'mdi-store',
                title: 'Revenue',
                value: '$34,245',
            },
            {
                actionIcon: 'mdi-history',
                actionText: 'Just Updated',
                color: 'info',
                icon: 'mdi-twitter',
                title: 'Followers',
                value: '+245',
            },
        ],
    })
}
</script>
