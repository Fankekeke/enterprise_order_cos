<template>
  <a-modal v-model="show" title="修改物流" @cancel="onClose" :width="950">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
<!--      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">-->
<!--        修改-->
<!--      </a-button>-->
    </template>
    <div style="font-size: 13px;font-family: SimHei">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">当前物流</span></a-col>
         <a-col :span="24">
          <a-table :columns="logisticsColumns" :data-source="logisticsList" :pagination="false">
          </a-table>
        </a-col>
      </a-row>
<!--      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="50">-->
<!--        <a-col :span="24">-->
<!--          <a-form-item label='物流备注' v-bind="formItemLayout">-->
<!--            <a-textarea :rows="6" v-model="remark"/>-->
<!--          </a-form-item>-->
<!--        </a-col>-->
<!--      </a-row>-->
    </div>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'logisticsEdit',
  props: {
    logisticsEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.logisticsEditVisiable
      },
      set: function () {
      }
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
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      logisticsList: [],
      remark: ''
    }
  },
  methods: {
    selectLogistics (orderId) {
      this.$get(`/cos/logistics-info/order/${orderId}`).then((r) => {
        this.logisticsList = r.data.data
      })
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...logistics}) {
      this.rowId = logistics.orderId
      this.selectLogistics(logistics.orderId)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.remark = ''
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (!err) {
          this.loading = true
          this.$put('/cos/logistics-info/updateLogisticsOrder', {
            'orderId': this.rowId,
            'remark': this.remark
          }).then((r) => {
            this.reset()
            this.remark = ''
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
