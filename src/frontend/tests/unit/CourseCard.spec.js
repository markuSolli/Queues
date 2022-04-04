import { shallowMount } from '@vue/test-utils'
import CourseCard from '@/components/CourseCard.vue'
import { createStore } from 'vuex'


describe('CourseCard.vue test', () => {
  it('renders title of coursecard', () => {
    const store = createStore({
      role: "ADMIN"
    })
    // render the component
    const wrapper = shallowMount(CourseCard, {
      global: {
        provide: {
          store: store
        },
      },
        propsData: {
          cardInQueue: true,
          course: {
            code: "CODE1",
            title: "TITLE",
          }
          }
    })

    expect(wrapper.find('.queue-element-1').text()).toBe("CODE1 TITLE")
    expect(wrapper.find('.queue-element-4').text()).toBe("Progress:")
  })
})