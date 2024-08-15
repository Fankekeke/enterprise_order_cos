<template>
  <a-modal v-model="show" title="订单详情" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderDetailInfo !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="待付款" />
          <a-step title="已下单" />
          <a-step title="配送中" />
          <a-step title="已收货" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">订单信息</span></a-col>
        <a-col :span="8"><b>工单编号：</b>
          {{ orderDetailInfo.order.code }}
        </a-col>
        <a-col :span="8"><b>总价格：</b>
          {{ orderDetailInfo.totalPrice ? orderDetailInfo.totalPrice : '- -' }} 元
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          <span v-if="orderDetailInfo.type == 0">正常订单</span>
          <span v-if="orderDetailInfo.type == 1">预付款</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>订单状态：</b>
          <span v-if="orderDetailInfo.status == 0">待付款</span>
          <span v-if="orderDetailInfo.status == 1">已下单</span>
          <span v-if="orderDetailInfo.status == 2">配送中</span>
          <span v-if="orderDetailInfo.status == 3">已收货</span>
          <span v-if="orderDetailInfo.status == 4">已收货</span>
          <span v-if="orderDetailInfo.status == 5">已收货</span>
        </a-col>
        <a-col :span="8"><b>预付款金额：</b>
          {{ orderDetailInfo.subsistPrice ? orderDetailInfo.subsistPrice : '- -' }} 元
        </a-col>
        <a-col :span="8"><b>欠款金额：</b>
          {{ orderDetailInfo.owePrice ? orderDetailInfo.owePrice : '- -' }} 元
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>下单时间：</b>
          {{ orderDetailInfo.createDate ? orderDetailInfo.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>支付时间：</b>
          {{ orderDetailInfo.payDate ? orderDetailInfo.payDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>尾款支付时间：</b>
          {{ orderDetailInfo.oweDate ? orderDetailInfo.oweDate : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">用户信息</span></a-col>
        <a-col :span="8"><b>用户名称：</b>
            {{ orderDetailInfo.user.name }}
          </a-col>
        <a-col :span="8"><b>医院地址：</b>
          <span v-if="orderDetailInfo.user.type == 1">经销商</span>
          <span v-if="orderDetailInfo.user.type == 2">批发商</span>
          <span v-if="orderDetailInfo.user.type == 3">散客</span>
          <span v-if="orderDetailInfo.user.type == 4">代理商</span>
        </a-col>
        <a-col :span="8"><b>联系人：</b>
          {{ orderDetailInfo.user.contact }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>用户联系方式：</b>
          {{ orderDetailInfo.user.phone }}
        </a-col>
        <a-col :span="8"><b>用户注册时间：</b>
          {{ orderDetailInfo.user.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">收货地址</span></a-col>
        <a-col :span="8"><b>省份：</b>
          {{ orderDetailInfo.user.province }}
        </a-col>
        <a-col :span="8"><b>城市：</b>
          {{ orderDetailInfo.user.city }}
        </a-col>
        <a-col :span="8"><b>区：</b>
          {{ orderDetailInfo.user.area }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>联系方式：</b>
          {{ orderDetailInfo.user.phone }}
        </a-col>
        <a-col :span="8"><b>联系人：</b>
          {{ orderDetailInfo.user.contact }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>详细地址：</b>
          {{ orderDetailInfo.user.address }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">购买商品信息</span></a-col>
         <a-col :span="24">
          <a-table :columns="columns" :data-source="orderDetailInfo.detail">
          </a-table>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">当前物流</span></a-col>
         <a-col :span="24">
          <a-table :columns="logisticsColumns" :data-source="orderDetailInfo.logistics">
          </a-table>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'orderView',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.orderShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '商品名称',
        dataIndex: 'name'
      }, {
        title: '型号',
        dataIndex: 'model'
      }, {
        title: '商品图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '数量',
        dataIndex: 'num',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '单价',
        dataIndex: 'price',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '总金额',
        dataIndex: 'totalPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }]
    },
    logisticsColumns () {
      return [{
        title: '物流信息',
        dataIndex: 'remark'
      }, {
        title: '操作时间',
        dataIndex: 'createDate'
      }]
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      orderDetailInfo: null
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
        this.current = this.orderData.status
      }
    }
  },
  methods: {
    dataInit (orderId) {
      this.$get(`/cos/order-info/detail/${orderId}`).then((r) => {
        this.orderDetailInfo = r.data
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
