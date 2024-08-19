<template>
  <div style="width: 100%">
    <a-row style="margin-top: 25px">
      <a-row :gutter="20">
        <a-col :span="24">
          <div style="background: #ECECEC; padding: 30px;">
            <a-row :gutter="20">
              <a-col :span="8">
                <a-month-picker placeholder="选择月份" v-model="queryDate" style="width: 100%"/>
              </a-col>
              <a-col :span="1">
                <a-button type="primary" @click="selectRate">
                  查询
                </a-button>
              </a-col>
            </a-row>
          </div>
        </a-col>
        <a-col :span="12">
          <div hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">此月内入库量统计</p>
            <apexchart v-if="!chartLoading" type="radar" height="450" :options="chartOptions3" :series="series3"></apexchart>
          </div>
        </a-col>
        <a-col :span="12">
          <a-row :gutter="30" style="margin-top: 30px">
            <a-col :span="12" style="margin-top: 70px">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月订单量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.orderNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="12" style="margin-top: 70px">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月总收益</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.totalPrice }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="12" style="margin-top: 70px">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月入库单量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.putNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="12" style="margin-top: 70px">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月总支出</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.outlayPrice }}
                    <span style="font-size: 20px;margin-top: 3px">元</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </a-col>
        <a-col :span="8">
          <div hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">商品销量排行</p>
            <apexchart v-if="!chartLoading" type="pie" height="350" :options="chartOptions4" :series="series4"></apexchart>
          </div>
        </a-col>
        <a-col :span="8">
          <div hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">商品销售排行</p>
            <apexchart v-if="!chartLoading" type="radialBar" height="350" :options="chartOptions5" :series="series5"></apexchart>
          </div>
        </a-col>
        <a-col :span="8">
          <div hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">销售商品分类</p>
            <apexchart v-if="!chartLoading" type="pie" height="350" :options="chartOptions6" :series="series6"></apexchart>
          </div>
        </a-col>
        <a-col :span="24">
          <a-card hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">此月内订单收益统计</p>
            <apexchart v-if="!chartLoading" type="line" height="350" :options="chartOptions" :series="series"></apexchart>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">此月内订单量统计</p>
            <apexchart v-if="!chartLoading" type="area" height="350" :options="chartOptions1" :series="series1"></apexchart>
          </a-card>
        </a-col>
        <a-col :span="24">
          <a-card hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <p v-if="!chartLoading" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">此月内入库成本统计</p>
            <apexchart v-if="!chartLoading" type="bar" height="350" :options="chartOptions2" :series="series2"></apexchart>
          </a-card>
        </a-col>
      </a-row>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'House',
  data () {
    return {
      advanced: false,
      queryParams: {},
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 12,
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      titleData: {
        orderNum: 0,
        totalPrice: 0,
        putNum: 0,
        outlayPrice: 0
      },
      dataList: [],
      loading: false,
      chartLoading: false,
      checkFlag: '1',
      series: [{
        data: [34, 44, 54, 21, 12, 43, 33, 23, 66, 66, 58]
      }],
      chartOptions: {
        chart: {
          type: 'line',
          height: 350
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series1: [{
        name: 'STOCK ABC',
        data: [34, 44, 54, 21, 12, 43, 33, 23, 66, 66, 58]
      }],
      chartOptions1: {
        chart: {
          type: 'area',
          height: 350,
          zoom: {
            enabled: false
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
        yaxis: {
          opposite: true
        },
        legend: {
          horizontalAlign: 'left'
        }
      },
      series2: [{
        data: [21, 22, 10, 28, 16, 21, 13, 30]
      }],
      chartOptions2: {
        chart: {
          height: 350,
          type: 'bar',
          events: {
            click: function (chart, w, e) {
              // console.log(chart, w, e)
            }
          }
        },
        plotOptions: {
          bar: {
            columnWidth: '45%',
            distributed: true
          }
        },
        dataLabels: {
          enabled: false
        },
        legend: {
          show: false
        },
        xaxis: {
          categories: [
            ['John', 'Doe'],
            ['Joe', 'Smith'],
            ['Jake', 'Williams'],
            'Amber',
            ['Peter', 'Brown'],
            ['Mary', 'Evans'],
            ['David', 'Wilson'],
            ['Lily', 'Roberts']
          ],
          labels: {
            style: {
              fontSize: '12px'
            }
          }
        }
      },
      series3: [{
        name: 'Series 1',
        data: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
      }],
      chartOptions3: {
        chart: {
          height: 450,
          type: 'radar'
        },
        yaxis: {
          stepSize: 20
        },
        xaxis: {
          categories: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
        }
      },
      series4: [44, 55, 13, 43, 22],
      chartOptions4: {
        chart: {
          width: 380,
          type: 'pie'
        },
        labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
      series5: [76, 67, 61, 90],
      chartOptions5: {
        chart: {
          height: 390,
          type: 'radialBar'
        },
        plotOptions: {
          radialBar: {
            offsetY: 0,
            startAngle: 0,
            endAngle: 270,
            hollow: {
              margin: 5,
              size: '30%',
              background: 'transparent',
              image: undefined
            },
            dataLabels: {
              name: {
                show: false
              },
              value: {
                show: false
              }
            },
            barLabels: {
              enabled: true,
              useSeriesColors: true,
              offsetX: -8,
              fontSize: '16px',
              formatter: function (seriesName, opts) {
                return seriesName + ':  ' + opts.w.globals.series[opts.seriesIndex]
              }
            }
          }
        },
        colors: ['#1ab7ea', '#0084ff', '#39539E', '#0077B5'],
        labels: ['Vimeo', 'Messenger', 'Facebook', 'LinkedIn'],
        responsive: [{
          breakpoint: 480,
          options: {
            legend: {
              show: false
            }
          }
        }]
      },
      series6: [25, 15, 44, 55, 41, 17],
      chartOptions6: {
        chart: {
          width: '100%',
          height: '100%',
          type: 'pie'
        },
        labels: [
          'Monday',
          'Tuesday',
          'Wednesday',
          'Thursday',
          'Friday',
          'Saturday'
        ],
        theme: {
          monochrome: {
            enabled: true
          }
        },
        plotOptions: {
          pie: {
            dataLabels: {
              offset: -5
            }
          }
        },
        grid: {
          padding: {
            top: 0,
            bottom: 0,
            left: 0,
            right: 0
          }
        },
        dataLabels: {
          formatter (val, opts) {
            const name = opts.w.globals.labels[opts.seriesIndex]
            return [name, val.toFixed(1) + '%']
          }
        },
        legend: {
          show: false
        }
      },
      queryDate: null
    }
  },
  watch: {
  },
  mounted () {
    this.selectRate()
  },
  methods: {
    // onChange (date, dateString) {
    //   this.queryDate = dateString
    // },
    search () {
      this.queryDate = moment(new Date())
      this.selectRate()
    },
    selectRate () {
      this.chartLoading = true
      this.$get(`/cos/order-info/statistics/month`, {
        date: this.queryDate
      }).then((r) => {
        this.titleData.orderNum = r.data.orderNum
        this.titleData.totalPrice = r.data.totalPrice
        this.titleData.putNum = r.data.putNum
        this.titleData.outlayPrice = r.data.outlayPrice

        this.series[0].data = r.data.orderPriceList
        this.series1[0].data = r.data.orderNumList
        this.series2[0].data = r.data.outlayPriceList
        this.series3[0].data = r.data.outlayNumList

        this.series4 = []
        this.chartOptions4.labels = []
        let saleTypeRankMapCopy = r.data.saleTypeRankMapCopy
        for (let key in saleTypeRankMapCopy) {
          this.series4.push(saleTypeRankMapCopy[key])
          this.chartOptions4.labels.push(key)
        }
        setTimeout(() => {
          this.chartLoading = false
        }, 200)
      })
    },
    pageChange (page, pageSize) {
      this.pagination.defaultCurrent = page
      this.search()
    },
    selectHospitalRate (params = {}) {
      this.loading = true
      console.log(this.pagination.defaultCurrent)
      params.current = this.pagination.defaultCurrent
      params.size = this.pagination.defaultPageSize
      this.$get(`/cos/doctor-info/page`, {
        ...params
      }).then((r) => {
        this.dataList = r.data.data.records
        this.pagination.defaultCurrent = r.data.data.current
        this.pagination.total = r.data.data.total
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-alert-message {
  font-size: 14px;
  font-family: SimHei;
}
</style>
