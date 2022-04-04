import { shallowMount } from '@vue/test-utils'
import TaskList from '@/components/TaskList.vue'

describe('TaskList.vue test', () => {
  it('renders title of tasklist', () => {
    // render the component
    const wrapper = shallowMount(TaskList, {
        propsData: {
            title: "title",
            list: [],
          }
    })

    expect(wrapper.find('.add-row-left').text()).toBe("title")
  })
})