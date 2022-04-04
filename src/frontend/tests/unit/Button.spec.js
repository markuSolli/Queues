import { shallowMount } from '@vue/test-utils'
import Button from "@/components/Button.vue"


describe('Button.vue test', () => {
  it('renders title of button', () => {
    // render the component
    const wrapper = shallowMount(Button, {
        propsData: {
            title: "buttonTitle"
          }
    })

    expect(wrapper.find('#button').text()).toBe("buttonTitle")
  })
})