<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="文件名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.fileName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="关键词"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.keyWord"/>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add">语音上传解析</a-button>
        <a-button type="danger" @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :rowClassName="(record, index) => (index % 2 === 1 ? 'table-striped' : null)"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-badge status="processing" v-if="record.rackUp === 1"/>
            <a-badge status="error" v-if="record.rackUp === 0"/>
            <a-tooltip>
              <template slot="title">
                {{ record.title }}
              </template>
              {{ record.title.slice(0, 13) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="fileNameSlot" slot-scope="text, record">
          <template>
            <a-badge status="processing" v-if="record.status == 0"/>
            <a-badge status="success" v-if="record.status == 1"/>
            <a-badge status="error" v-if="record.status == 2"/>
            <a-tooltip>
              <template slot="title">
                {{ record.fileName }}
              </template>
              {{ record.fileName.slice(0, 10) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="paragraphTitleSlot" slot-scope="text, record">
          <template>
            <a-tooltip v-if="record.paragraphTitle">
              <template slot="title">
                {{ record.paragraphTitle }}
              </template>
              {{ record.paragraphTitle.slice(0, 10) }} ...
            </a-tooltip>
            <span v-else>- -</span>
          </template>
        </template>
        <template slot="paragraphSummarySlot" slot-scope="text, record">
          <template>
            <a-tooltip v-if="record.paragraphSummary">
              <template slot="title">
                {{ record.paragraphSummary }}
              </template>
              {{ record.paragraphSummary.slice(0, 18) }} ...
            </a-tooltip>
            <span v-else>- -</span>
          </template>
        </template>
        <template slot="keyWordSlot" slot-scope="text, record">
          <template>
            <a-tooltip v-if="record.keyWord">
              <template slot="title">
                {{ record.keyWord }}
              </template>
              {{ record.keyWord.slice(0, 10) }} ...
            </a-tooltip>
            <span v-else>- -</span>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="eye" theme="twoTone" twoToneColor="#4a9ff5" @click="recordViewOpen(record)" title="详 情"></a-icon>
        </template>
      </a-table>
    </div>
    <record-view
      @close="handleRecordViewClose"
      :recordShow="recordView.visiable"
      :recordData="recordView.data">
    </record-view>
    <record-add
      v-if="recordAdd.visiable"
      @close="handleRecordAddClose"
      @success="handleRecordAddSuccess"
      :recordAddVisiable="recordAdd.visiable">
    </record-add>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import recordView from './RecordView.vue'
import {mapState} from 'vuex'
import moment from 'moment'
import RecordAdd from './RecordAdd.vue'
moment.locale('zh-cn')

export default {
  name: 'record',
  components: {RecordAdd, RangeDate, recordView},
  data () {
    return {
      recordAdd: {
        visiable: false
      },
      advanced: false,
      recordView: {
        visiable: false,
        data: null
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '文件名称',
        dataIndex: 'fileName',
        width: 200,
        scopedSlots: {customRender: 'fileNameSlot'}
      }, {
        title: '音频时长（分钟）',
        dataIndex: 'voiceDuration',
        width: 150,
        customRender: (text, row, index) => {
          if (text !== null) {
            return this.formatDateCheck(text)
          } else {
            return '- -'
          }
        }
      }, {
        title: '解析状态',
        dataIndex: 'status',
        width: 150,
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag color="blue">处理中</a-tag>
            case '1':
              return <a-tag color="green">已完成</a-tag>
            case '2':
              return <a-tag color="red">处理错误</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '摘要标题',
        dataIndex: 'paragraphTitle',
        width: 200,
        scopedSlots: {customRender: 'paragraphTitleSlot'}
      }, {
        title: '摘要内容',
        dataIndex: 'paragraphSummary',
        width: 300,
        scopedSlots: {customRender: 'paragraphSummarySlot'}
      }, {
        title: '关键词',
        dataIndex: 'keyWord',
        width: 200,
        scopedSlots: {customRender: 'keyWordSlot'}
      }, {
        title: '上传时间',
        dataIndex: 'createDate',
        width: 200,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    /**
     * 音频时间格式化
     * @param millisecond 毫秒
     * @returns {number|*} 结果
     */
    formatDateCheck (millisecond) {
      if (millisecond > 0) {
        const date = moment.duration(millisecond, 'millisecond')
        const hours = Math.floor(date._data.hours)
        const minutes = Math.floor(date._data.minutes)
        const seconds = Math.floor(date._data.seconds)
        return (hours > 0 ? `${hours < 10 ? '0' : ''}${hours}:` : '00:') +
          (minutes > 0 ? `${minutes < 10 ? '0' : ''}${minutes}:` : '00:') +
          (seconds > 0 ? `${seconds < 10 ? '0' : ''}${seconds}` : '00')
      } else {
        return 0
      }
    },
    add () {
      this.recordAdd.visiable = true
    },
    handleRecordAddClose () {
      this.recordAdd.visiable = false
    },
    handleRecordAddSuccess () {
      this.recordAdd.visiable = false
      this.$message.success('上传成功')
      this.search()
    },
    recordViewOpen (row) {
      this.recordView.data = row
      this.recordView.visiable = true
    },
    handleRecordViewClose () {
      this.recordView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/voice-analysis-record/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      this.$get('/cos/voice-analysis-record/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
