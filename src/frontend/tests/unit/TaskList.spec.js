import { shallowMount } from '@vue/test-utils'
import TaskList from '@/components/TaskList.vue'

describe('TaskList.vue test', () => {
  it('renders title of tasklist', () => {
    const $store = {
      state: {
        count: 25
      },
    }

    // render the component
    const wrapper = shallowMount(TaskList, {
      global: {
        mocks: {
          $store
        }
      },
        propsData: {
            title: "title",
            list: [],
          }
    })

    expect(wrapper.find('.add-row-left').text()).toBe("title")
  })
})