import { shallowMount } from '@vue/test-utils'
import CourseCard from '@/components/CourseCard.vue'


describe('CourseCard.vue test', () => {
  it('renders message when component is created', () => {
    // render the component
    const wrapper = shallowMount(Header, {
      propsData: {
        title: 'Vue Project'
      }
    })

    // check the name of the component
    expect(wrapper.vm.$options.name).toMatch('Header')

    // check that the title is rendered
    expect(wrapper.text()).toMatch('Vue Project')
  })
})