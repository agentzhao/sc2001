void findMinMax(int[] ar, int start, int end, int[] minmax) {
  int tempMinMax1[] = new int[2];
  int tempMinMax2[] = new int[2];
  int mid;

  if (start == end){
    minmax[0] = ar[start];
    minmax[1] = ar[start];
    return;
  } else if (end - start == 1){
    if ar[start] < ar[end]{
      minmax[0] = ar[start];
      minmax[1] = ar[end];
    } else {
      minmax[0] = ar[end];
      minmax[1] = ar[start];
    }
  } else {
    mid = (start + end) / 2;
    findMinMax(ar, start, mid, tempMinMax1);
    findMinMax(ar, mid + 1, end, tempMinMax2);
    if (tempMinMax1[0] < tempMinMax2[0]){
      minmax[0] = tempMinMax1[0];
    } else {
      minmax[0] = tempMinMax2[0];
    }
    if (tempMinMax1[1] > tempMinMax2[1]){
      minmax[1] = tempMinMax1[1];
    } else {
      minmax[1] = tempMinMax2[1];
    }
  }
}
